package org.mdmi.rt.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import org.hl7.fhir.instance.model.api.IBaseResource;
import org.mdmi.core.Mdmi;
import org.mdmi.core.runtime.RuntimeService;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.parser.IParserErrorHandler;
import ca.uhn.fhir.parser.json.JsonLikeValue.ScalarType;
import ca.uhn.fhir.parser.json.JsonLikeValue.ValueType;

public class Run3 {

	public static void main(String[] args) throws FileNotFoundException, Exception {

		try {

			System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "trace");

			// Start of MDMI - Properties are used to customize aspects of maps - not needed yet
			HashMap<String, String> sourceProperties = new HashMap<String, String>();
			HashMap<String, String> targetProperties = new HashMap<String, String>();

			// Load the source maps
			File sourceFile = new File("maps/dr/DocumentList.mdmi");
			InputStream sourceStream = new FileInputStream(sourceFile);
			Mdmi.INSTANCE().getResolver().resolve(sourceStream);

			// Load the target map
			File targetFile = new File("maps/dr/stu4fhirDocumentReference.mdmi");
			InputStream targetStream = new FileInputStream(targetFile);
			Mdmi.INSTANCE().getResolver().resolve(targetStream);

			// Save merge document to byte array for MDMI
			// ByteArrayOutputStream bos = new ByteArrayOutputStream();

			String content = new String(Files.readAllBytes(Paths.get("maps/dr/DRListExample3.xml")));

			IParserErrorHandler noop = new IParserErrorHandler() {

				@Override
				public void containedResourceWithNoId(IParseLocation arg0) {
					System.out.println("containedResourceWithNoId");
					// TODO Auto-generated method stub

				}

				@Override
				public void incorrectJsonType(IParseLocation arg0, String arg1, ValueType arg2, ScalarType arg3,
						ValueType arg4, ScalarType arg5) {
					System.out.println("incorrectJsonType");
					// TODO Auto-generated method stub

				}

				@Override
				public void invalidValue(IParseLocation arg0, String arg1, String arg2) {
					System.out.println("invalidValue" + arg2);

				}

				@Override
				public void missingRequiredElement(IParseLocation arg0, String arg1) {
					System.out.println("missingRequiredElement");

				}

				@Override
				public void unexpectedRepeatingElement(IParseLocation arg0, String arg1) {
					System.out.println("unexpectedRepeatingElement");

				}

				@Override
				public void unknownAttribute(IParseLocation arg0, String arg1) {
					System.out.println("unknownAttribute");

				}

				@Override
				public void unknownElement(IParseLocation arg0, String arg1) {
					System.out.println("unknownElement" + arg0);
					System.out.println("unknownElement" + arg1);

				}

				@Override
				public void unknownReference(IParseLocation arg0, String arg1) {
					System.out.println("unknownReference");

				}
			};
			// Run the transformation
			String result = RuntimeService.runTransformation(
				"IHE.DocumentList", content.getBytes(), "R4JSON.DocumentReference", "resources/results",
				sourceProperties, targetProperties);

			System.out.println(result);
			FhirContext ctx = FhirContext.forR4();
			IParser parse = ctx.newJsonParser();
			parse.setParserErrorHandler(noop);
			IBaseResource bundle = parse.parseResource(result);
			System.out.println(ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(bundle));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
