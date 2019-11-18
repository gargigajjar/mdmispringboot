package org.mdmi.rt.service.sql;

import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class AClass {

	enum TestTableColumns {
		id, TEXT;
	}

	private final String jdbcDriverStr;

	private final String jdbcURL;

	private Connection connection;

	private Statement statement;

	private ResultSet resultSet;

	private PreparedStatement preparedStatement;

	public AClass(String jdbcDriverStr, String jdbcURL) {
		this.jdbcDriverStr = jdbcDriverStr;
		this.jdbcURL = jdbcURL;
	}

	public void readData() throws Exception {
		try {
			Class.forName(jdbcDriverStr);
			connection = DriverManager.getConnection(jdbcURL);
			statement = connection.createStatement();

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.newDocument();
			Element root = doc.createElement("root");
			doc.appendChild(root);
			Element results = doc.createElement("NYOMHrecord");
			root.appendChild(results);

			resultSet = statement.executeQuery("select * from testdata.demographics where MEDICAID_ID='111222333';");
			getResultSet(resultSet, doc, results, "Demographics");
			resultSet = statement.executeQuery("select * from testdata.medications where MEDICAID_ID='111222333';");
			getResultSet(resultSet, doc, results, "Medications");
			resultSet = statement.executeQuery("select * from testdata.encounters where MEDICAID_ID='111222333';");
			getResultSet(resultSet, doc, results, "Encounters");
			resultSet = statement.executeQuery("select * from testdata.problems where MEDICAID_ID='111222333';");
			getResultSet(resultSet, doc, results, "Problems");

			DOMSource domSource = new DOMSource(doc);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
			StringWriter sw = new StringWriter();
			StreamResult sr = new StreamResult(sw);
			transformer.transform(domSource, sr);

			System.out.println(sw.toString());

			// preparedStatement = connection.prepareStatement("insert into javaTestDB.test_table values (default,?)");
			// preparedStatement.setString(1, "insert test from java");
			// preparedStatement.executeUpdate();
		} finally {
			close();
		}
	}

	public byte[] readData(String patientID) throws Exception {
		StringWriter sw = new StringWriter();
		try {
			Class.forName(jdbcDriverStr);
			connection = DriverManager.getConnection(jdbcURL);
			statement = connection.createStatement();

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.newDocument();
			Element root = doc.createElement("root");
			doc.appendChild(root);
			Element results = doc.createElement("NYOMHrecord");
			root.appendChild(results);
			String query = "select * from testdata.demographics where MEDICAID_ID='" + "patientID" + "';";
			System.out.println(query);
			resultSet = statement.executeQuery(query);
			getResultSet(resultSet, doc, results, "Demographics");
			resultSet = statement.executeQuery(
				"select * from testdata.medications where MEDICAID_ID='" + "patientID" + "';");
			getResultSet(resultSet, doc, results, "Medications");
			resultSet = statement.executeQuery(
				"select * from testdata.encounters where MEDICAID_ID='" + "patientID" + "';");
			getResultSet(resultSet, doc, results, "Encounters");
			resultSet = statement.executeQuery(
				"select * from testdata.problems where MEDICAID_ID='" + "patientID" + "';");
			getResultSet(resultSet, doc, results, "Problems");

			DOMSource domSource = new DOMSource(doc);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");

			StreamResult sr = new StreamResult(sw);
			transformer.transform(domSource, sr);
		} finally {
			close();
		}

		return sw.toString().getBytes();
	}

	private void getResultSet(ResultSet rs, Document doc, Element results, String rowName) throws Exception {

		ResultSetMetaData rsmd = rs.getMetaData();
		int colCount = rsmd.getColumnCount();

		while (rs.next()) {
			Element row = doc.createElement(rowName);
			results.appendChild(row);
			for (int i = 1; i <= colCount; i++) {
				String columnName = rsmd.getColumnName(i);
				Object value = rs.getObject(i);
				Element node = doc.createElement(columnName);
				node.appendChild(doc.createTextNode(value.toString()));
				row.appendChild(node);
			}
		}

		// while (resultSet.next()) {
		// // Integer id = resultSet.getInt(TestTableColumns.id.toString());
		// // String text = resultSet.getString(TestTableColumns.TEXT.toString());
		// // System.out.println("id: " + id);
		// // System.out.println("text: " + text);
		// }
	}

	private void close() {
		try {
			if (resultSet != null)
				resultSet.close();
			if (statement != null)
				statement.close();
			if (connection != null)
				connection.close();
		} catch (Exception e) {
		}
	}
}
