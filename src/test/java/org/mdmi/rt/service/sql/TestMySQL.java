/*******************************************************************************
 * Copyright (c) 2019 seanmuir.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     seanmuir - initial API and implementation
 *
 *******************************************************************************/
package org.mdmi.rt.service.sql;

import org.junit.Test;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;

/**
 * @author seanmuir
 *
 */
public class TestMySQL {

	private static Logger logger = LoggerFactory.getLogger(TestMySQL.class);
	// mdixdb.c0b4at8xfg9n.us-east-1.rds.amazonaws.com

	public static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";

	public static final String MYSQL_URL = "jdbc:mysql://mdixdb.c0b4at8xfg9n.us-east-1.rds.amazonaws.com/testdata?" +
			"user=MDIXMySql&password=SQL3q20!9Today";

	@Test
	public void test() throws Exception {

		LoggerContext foobar;

		Logger root = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

		// LoggerFactory.getILoggerFactory().

		// logger.debug(root.getClass().getCanonicalName());

		ILoggerFactory ll = LoggerFactory.getILoggerFactory();
		// SimpleLoggerFactory nop = (SimpleLoggerFactory) ll;

		// nop.

		AClass dao = new AClass(MYSQL_DRIVER, MYSQL_URL);
		dao.readData("111222333");
		dao.readData();
	}

}
