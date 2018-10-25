/*******************************************************************************
 * Copyright (c) 2009, 2018 Mountainminds GmbH & Co. KG and Contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Marc R. Hoffmann - initial API and implementation
 *    
 *******************************************************************************/
package org.jacoco.examples;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import org.jacoco.core.analysis.Analyzer;
import org.jacoco.core.analysis.IClassCoverage;
import org.jacoco.core.analysis.ICoverageVisitor;
import org.jacoco.core.data.ExecutionDataStore;

/**
 * This example reads Java class files, directories or JARs given as program
 * arguments and dumps information about the classes.
 */
/**
 * 分析class信息，包括：
 * class name:   com/cqrcb/ncbs/CodeCoverage/App  类名
 * class id:     3f3ba0079713120c  
 * instructions: 7            类指令数，包括括号，不包括空格
 * branches:     0            类分支数
 * lines:        3			      类代码行数，包括类名，方法名，不包括括号，空格
 * methods:      2            类中方法数，包含默认构造方法
 * complexity:   2            类复杂度
 * @author zj
 *
 */
public final class ClassInfo implements ICoverageVisitor {

	private final PrintStream out;
	private final Analyzer analyzer;

	/**
	 * Creates a new example instance printing to the given stream.
	 * 
	 * @param out
	 *            stream for outputs
	 */
	public ClassInfo(final PrintStream out) {
		this.out = out;
		analyzer = new Analyzer(new ExecutionDataStore(), this);
	}

	/**
	 * Run this example with the given parameters.
	 * 
	 * @param args
	 *            command line parameters
	 * @throws IOException
	 *             in case of error reading a input file
	 */
	public void execute(final String[] args) throws IOException {
		for (final String file : args) {
			analyzer.analyzeAll(new File(file));
		}
	}

	public void visitCoverage(final IClassCoverage coverage) {
		//类名
		out.printf("class name:   %s%n", coverage.getName());
		out.printf("class id:     %016x%n", Long.valueOf(coverage.getId()));
		out.printf("instructions: %s%n", Integer.valueOf(coverage
				.getInstructionCounter().getTotalCount()));
		out.printf("branches:     %s%n",
				Integer.valueOf(coverage.getBranchCounter().getTotalCount()));
		out.printf("lines:        %s%n",
				Integer.valueOf(coverage.getLineCounter().getTotalCount()));
		out.printf("methods:      %s%n",
				Integer.valueOf(coverage.getMethodCounter().getTotalCount()));
		out.printf("complexity:   %s%n%n", Integer.valueOf(coverage
				.getComplexityCounter().getTotalCount()));
	}

	/**
	 * Entry point to run this examples as a Java application.
	 * 
	 * @param args
	 *            list of program arguments
	 * @throws IOException
	 *             in case of errors executing the example
	 */
	public static void main(final String[] args) throws IOException {
		new ClassInfo(System.out).execute(args);
	}

}
