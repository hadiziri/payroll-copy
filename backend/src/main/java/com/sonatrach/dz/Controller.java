package com.sonatrach.dz;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.sonatrach.dz.banque.domain.Banque;
import com.sonatrach.dz.banque.repo.BanqueRepo;
import com.sonatrach.dz.cloturePaie.domain.CloturePaie;
import com.sonatrach.dz.cloturePaie.repo.CloturePaieRepo;
import com.sonatrach.dz.diplome.domain.Diplome;
import com.sonatrach.dz.diplome.repo.DiplomeRepo;
import com.sonatrach.dz.fonction.domain.Fonction;
import com.sonatrach.dz.fonction.repo.FonctionRepo;
import com.sonatrach.dz.localite.domain.Localite;
import com.sonatrach.dz.localite.repo.LocaliteRepo;
import com.sonatrach.dz.pays.domain.Pays;
import com.sonatrach.dz.pays.repo.PaysRepo;
import com.sonatrach.dz.rubrique.domain.Rubrique;
import com.sonatrach.dz.rubrique.repo.RubriqueRepo;
import com.sonatrach.dz.structure.domain.Structure;
import com.sonatrach.dz.structure.repo.StructureRepo;
import com.sonatrach.dz.tabstructure.domain.TabStructure;
import com.sonatrach.dz.tabstructure.repo.TabStructureRepo;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JRCsvDataSource;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class Controller {
	// **********************************************repositories************************************************************
	@Autowired
	BanqueRepo banqueRepo;
	@Autowired
	StructureRepo structureRepo;
	@Autowired
	CloturePaieRepo clotureRepo;
	@Autowired
	DiplomeRepo diplomeRepo;
	@Autowired
	FonctionRepo fonctionRepo;
	@Autowired
	LocaliteRepo localiteRepo;
	@Autowired
	PaysRepo paysRepo;
	@Autowired
	RubriqueRepo rubriqueRepo;
	@Autowired
	TabStructureRepo tabStructureRepo;

	// ****************************************API*****************************************************************************
	@GetMapping("/allBanques")
	public List<Fonction> getAllBanques() {
		return fonctionRepo.findAll();
	}

	@GetMapping("/getAllStructures")
	public List<Structure> getAllStructures() {
		List<Structure> lesStructures = new ArrayList();
		try {
			lesStructures = structureRepo.findAll();
			return lesStructures;
		} catch (Exception e) {
			System.out.println("Exception getAllStructures()==>" + e.getMessage());
		}
		return lesStructures;
	}

	@GetMapping("generateClotureFiles")
	public List<CloturePaie> cloturePaie() {
		List<CloturePaie> toutLesFichiers = new ArrayList();
		try {

			// ***************************get all files to generate and its directory path
			toutLesFichiers = clotureRepo.findAll();
			for (int i = 0; i < toutLesFichiers.size(); i++) {
				String path = toutLesFichiers.get(i).getFOLDERPATH();
				String folderName = toutLesFichiers.get(i).getFOLDERNAME();
				String fileName = toutLesFichiers.get(i).getPREFIXFILETYPE();
				String description = toutLesFichiers.get(i).getDESCFILETYPE().toString();
				generateFiles(path, folderName, fileName, description);
			}

		} catch (Exception e) {
			System.out.println("Exception while generating file==>" + e);
		}

		return toutLesFichiers;
	}

	// ****************************************************Methodes****************************************************************

	// ----To generate Table files (DIN-DRH==>cloture paie)------
	private void generateFiles(String path, String folderName, String fileName, String description)
			throws FileNotFoundException, JRException {
		// **********************************************get sys date
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat year = new SimpleDateFormat("yyyy");

		// ********************************folder generation if not exist
		String pathWithYear = path + folderName + "\\" + year.format(date);
		String pathWithMounth = pathWithYear + "\\" + formatter.format(date);
		File fileYear = new File(pathWithYear);
		if (!fileYear.exists()) {
			fileYear.mkdir();
		} else {
			File fileMounth = new File(pathWithMounth);
			if (!fileMounth.exists()) {
				fileMounth.mkdir();
			}
		}
		switch (description) {
		case "banque":
			List<Banque> lesBanques = banqueRepo.findAll();
			// load file and compile it
			File file = ResourceUtils.getFile("classpath:lesBanques.jrxml");
			JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lesBanques);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);
			JRXlsxExporter exporter = new JRXlsxExporter();
			exporter.setParameter(JRTextExporterParameter.PAGE_WIDTH, 80);
			exporter.setParameter(JRTextExporterParameter.PAGE_HEIGHT, 40);
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			Object outputFileName = pathWithMounth + "\\" + fileName + " " + formatter.format(date) + ".xlsx";
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outputFileName);
			exporter.exportReport();
			break;

		case "diplome":
			List<Diplome> lesDiplomes = diplomeRepo.findAll();
			// load file and compile it
			File fileDiplome = ResourceUtils.getFile("classpath:lesDiplomes.jrxml");
			JasperReport jasperReport2 = JasperCompileManager.compileReport(fileDiplome.getAbsolutePath());
			JRBeanCollectionDataSource dataSource2 = new JRBeanCollectionDataSource(lesDiplomes);
			JasperPrint jasperPrint2 = JasperFillManager.fillReport(jasperReport2, null, dataSource2);
			JRXlsxExporter exporter2 = new JRXlsxExporter();
			exporter2.setParameter(JRTextExporterParameter.PAGE_WIDTH, 80);
			exporter2.setParameter(JRTextExporterParameter.PAGE_HEIGHT, 40);
			exporter2.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint2);
			Object outputFileName2 = pathWithMounth + "\\" + fileName + " " + formatter.format(date) + ".xlsx";
			exporter2.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outputFileName2);
			exporter2.exportReport();
			break;

		case "fonction":
			List<Fonction> lesFonctions = fonctionRepo.findAll();
			// load file and compile it
			File fileFonctions = ResourceUtils.getFile("classpath:lesFonctions.jrxml");
			JasperReport jasperReport3 = JasperCompileManager.compileReport(fileFonctions.getAbsolutePath());
			JRBeanCollectionDataSource dataSource3 = new JRBeanCollectionDataSource(lesFonctions);
			JasperPrint jasperPrint3 = JasperFillManager.fillReport(jasperReport3, null, dataSource3);
			JRXlsxExporter exporter3 = new JRXlsxExporter();
			exporter3.setParameter(JRTextExporterParameter.PAGE_WIDTH, 80);
			exporter3.setParameter(JRTextExporterParameter.PAGE_HEIGHT, 40);
			exporter3.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint3);
			Object outputFileName3 = pathWithMounth + "\\" + fileName + " " + formatter.format(date) + ".xlsx";
			exporter3.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outputFileName3);
			exporter3.exportReport();
			break;

		case "localite":
			List<Localite> lesLocalites = localiteRepo.findAll();
			// load file and compile it
			File fileLocalite = ResourceUtils.getFile("classpath:lesLocalites.jrxml");
			JasperReport jasperReport4 = JasperCompileManager.compileReport(fileLocalite.getAbsolutePath());
			JRBeanCollectionDataSource dataSource4 = new JRBeanCollectionDataSource(lesLocalites);
			JasperPrint jasperPrint4 = JasperFillManager.fillReport(jasperReport4, null, dataSource4);
			JRXlsxExporter exporter4 = new JRXlsxExporter();
			exporter4.setParameter(JRTextExporterParameter.PAGE_WIDTH, 80);
			exporter4.setParameter(JRTextExporterParameter.PAGE_HEIGHT, 40);
			exporter4.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint4);
			Object outputFileName4 = pathWithMounth + "\\" + fileName + " " + formatter.format(date) + ".xlsx";
			exporter4.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outputFileName4);
			exporter4.exportReport();
			break;

		case "pays":
			List<Pays> lesPays = paysRepo.findAll();
			// load file and compile it
			File filePays = ResourceUtils.getFile("classpath:lesPays.jrxml");
			JasperReport jasperReport5 = JasperCompileManager.compileReport(filePays.getAbsolutePath());
			JRBeanCollectionDataSource dataSource5 = new JRBeanCollectionDataSource(lesPays);
			JasperPrint jasperPrint5 = JasperFillManager.fillReport(jasperReport5, null, dataSource5);
			JRXlsxExporter exporter5 = new JRXlsxExporter();
			exporter5.setParameter(JRTextExporterParameter.PAGE_WIDTH, 80);
			exporter5.setParameter(JRTextExporterParameter.PAGE_HEIGHT, 40);
			exporter5.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint5);
			Object outputFileName5 = pathWithMounth + "\\" + fileName + " " + formatter.format(date) + ".xlsx";
			exporter5.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outputFileName5);
			exporter5.exportReport();
			break;

		case "rubrique":
			List<Rubrique> lesRubriques = rubriqueRepo.findAll();
			// load file and compile it
			File fileRubrique = ResourceUtils.getFile("classpath:lesRubriques.jrxml");
			JasperReport jasperReport6 = JasperCompileManager.compileReport(fileRubrique.getAbsolutePath());
			JRBeanCollectionDataSource dataSource6 = new JRBeanCollectionDataSource(lesRubriques);
			JasperPrint jasperPrint6 = JasperFillManager.fillReport(jasperReport6, null, dataSource6);
			JRXlsxExporter exporter6 = new JRXlsxExporter();
			exporter6.setParameter(JRTextExporterParameter.PAGE_WIDTH, 80);
			exporter6.setParameter(JRTextExporterParameter.PAGE_HEIGHT, 40);
			exporter6.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint6);
			Object outputFileName6 = pathWithMounth + "\\" + fileName + " " + formatter.format(date) + ".xlsx";
			exporter6.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outputFileName6);
			exporter6.exportReport();
			break;

		case "structure":
			List<TabStructure> lesTabStructures = tabStructureRepo.findAll();
			// load file and compile it
			File fileTabStructure = ResourceUtils.getFile("classpath:lesTabStructures.jrxml");
			JasperReport jasperReport7 = JasperCompileManager.compileReport(fileTabStructure.getAbsolutePath());
			JRBeanCollectionDataSource dataSource7 = new JRBeanCollectionDataSource(lesTabStructures);
			JasperPrint jasperPrint7 = JasperFillManager.fillReport(jasperReport7, null, dataSource7);
			JRXlsxExporter exporter7 = new JRXlsxExporter();
			exporter7.setParameter(JRTextExporterParameter.PAGE_WIDTH, 80);
			exporter7.setParameter(JRTextExporterParameter.PAGE_HEIGHT, 40);
			exporter7.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint7);
			Object outputFileName7 = pathWithMounth + "\\" + fileName + " " + formatter.format(date) + ".xlsx";
			exporter7.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outputFileName7);
			exporter7.exportReport();
			break;

		default:
			System.out.println("no match");

		}
	}

}
