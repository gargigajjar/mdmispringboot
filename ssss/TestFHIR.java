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
package org.mdmi.rt.service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.conformance.ProfileUtilities;
import org.hl7.fhir.r4.conformance.ProfileUtilities.ProfileKnowledgeProvider;
import org.hl7.fhir.r4.context.IWorkerContext;
import org.hl7.fhir.utilities.validation.ValidationMessage;
import org.junit.Test;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.validation.FhirValidator;
import ca.uhn.fhir.validation.ValidationResult;

/**
 * @author seanmuir
 *
 */
public class TestFHIR {

	@Test
	public void test() throws IOException {

		FhirContext ctx = FhirContext.forR4();

		String content = new String(
			Files.readAllBytes(Paths.get("temp/StructureDefinition-us-core-immunization.json")));

		// Bundle bundle = ctx.newJsonParser().parseResource(Bundle.class, mdmiMessage.getDataAsString());

		IBaseResource b = ctx.newJsonParser().parseResource(content);

		System.out.println(ctx.newXmlParser().encodeResourceToString(b));

		// mdmiMessage.setData(ctx.newXmlParser().encodeResourceToString(b));
	}

	@Test
	public void testValidate() throws Exception {

		FhirContext ctx = FhirContext.forR4();

		IWorkerContext aaa = null;
		List<ValidationMessage> bbb = new ArrayList<ValidationMessage>();
		ProfileKnowledgeProvider ccc;
		 ProfileUtilities asdfasdf = new ProfileUtilities(aaa, bbb, ccc);
		 
		 asdfasdf.generateSnapshot(base, derived, url, profileName);

		// Create a validator and configure it
		FhirValidator validator = ctx.newValidator();
		validator.setValidateAgainstStandardSchema(true);
		validator.setValidateAgainstStandardSchematron(true);

		// Get a list of files in a given directory
		String[] fileList = new File("/Users/seanmuir/FHIRModel-EclipseApplication/FHIRLatest/t2").list(
			new WildcardFileFilter("*.xml"));
		for (String nextFile : fileList) {
			System.out.println(nextFile);
			// For each file, load the contents into a string
			String nextFileContents = IOUtils.toString(
				new FileReader("/Users/seanmuir/FHIRModel-EclipseApplication/FHIRLatest/t2/" + nextFile));

			// Parse that string (this example assumes JSON encoding)
			IBaseResource resource = ctx.newXmlParser().parseResource(nextFileContents);

			// Apply the validation. This will throw an exception on the first
			// validation failure
			ValidationResult result = validator.validateWithResult(resource);
			if (result.isSuccessful() == false) {
				throw new Exception("We failed!");
			}

		}
	}

}
