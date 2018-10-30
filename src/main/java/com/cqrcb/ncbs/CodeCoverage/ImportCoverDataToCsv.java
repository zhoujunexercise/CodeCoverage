package com.cqrcb.ncbs.CodeCoverage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.jacoco.core.analysis.Analyzer;
import org.jacoco.core.analysis.CoverageBuilder;
import org.jacoco.core.analysis.IBundleCoverage;
import org.jacoco.core.tools.ExecFileLoader;
import org.jacoco.report.DirectorySourceFileLocator;
import org.jacoco.report.FileMultiReportOutput;
import org.jacoco.report.IReportVisitor;
import org.jacoco.report.csv.CSVFormatter;


/**
 * 将jacoco.exec中的覆盖率数据解析成CSV格式.
 * 
 * 
 * 
 * @author zhoujun
 *
 */
public class ImportCoverDataToCsv
{
	//parent文件路径
	File  projectDirectory = new File("C:\\Users\\zj\\eclipse-workspace\\CodeCoverage\\src\\main\\resources\\tmp\\");
	//jacoco.exec文件夹
	private String execDirectoryName="jacoco.exec";
	//classes文件夹名
	private String classesDirectoryName="classes";
	//源码文件夹名
	private String sourceDirectoryName="source";
	//CSV文件名
	private String csvDirectoryName="output.csv";
	
	private  File executionDataFile;
	private  File classesDirectory;
	private  File sourceDirectory;
	private  File csvDirectory;
	
	private ExecFileLoader execFileLoader;
	public void create() throws IOException
	{
		
		initFile();
		execFileLoader = new ExecFileLoader();
		execFileLoader.load(executionDataFile);
		IBundleCoverage bundleCoverage = analyzeStructure();
		createCsv(bundleCoverage);
	}
	
	/**
	 * 初始化FILE.
	 * 
	 * 
	 */
	private void initFile()
	{
		this.executionDataFile = new File(projectDirectory,execDirectoryName);
		this.classesDirectory = new File(projectDirectory,classesDirectoryName);
		this.sourceDirectory = new File(projectDirectory, sourceDirectoryName);
		this.csvDirectory = new File(projectDirectory,csvDirectoryName);
	}
	
	
	private IBundleCoverage analyzeStructure() throws IOException {
		 CoverageBuilder coverageBuilder = new CoverageBuilder();
		 Analyzer analyzer = new Analyzer(
				execFileLoader.getExecutionDataStore(), coverageBuilder);

		analyzer.analyzeAll(classesDirectory);

		return coverageBuilder.getBundle("title");
	}
	
	
	private void createCsv(IBundleCoverage bundleCoverage) throws FileNotFoundException, IOException
	{
		CSVFormatter csvFormatter = new CSVFormatter();
		 IReportVisitor visitor = csvFormatter.createVisitor(new FileOutputStream(csvDirectory));
		 visitor.visitInfo(execFileLoader.getSessionInfoStore().getInfos(),
					execFileLoader.getExecutionDataStore().getContents());
		 visitor.visitBundle(bundleCoverage, new DirectorySourceFileLocator(
					sourceDirectory, "utf-8", 4));
		 visitor.visitEnd();
	}
	
	
	public static void main(String[] args) throws IOException
	{
		ImportCoverDataToCsv importCoverDataToCsv = new ImportCoverDataToCsv();
		
		importCoverDataToCsv.create();
		
	}
	
	
}
