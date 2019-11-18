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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.builder.Diff;
import org.apache.commons.lang3.builder.DiffResult;
import org.apache.xml.utils.TreeWalker;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.Basic;
import org.hl7.fhir.r4.model.BooleanType;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Bundle.BundleType;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.DateTimeType;
import org.hl7.fhir.r4.model.DiagnosticReport;
import org.hl7.fhir.r4.model.DocumentReference;
import org.hl7.fhir.r4.model.DocumentReference.DocumentReferenceContextComponent;
import org.hl7.fhir.r4.model.DocumentReference.ReferredDocumentStatus;
import org.hl7.fhir.r4.model.ElementDefinition;
import org.hl7.fhir.r4.model.Enumerations.DocumentReferenceStatus;
import org.hl7.fhir.r4.model.Identifier;
import org.hl7.fhir.r4.model.InstantType;
import org.hl7.fhir.r4.model.IntegerType;
import org.hl7.fhir.r4.model.Meta;
import org.hl7.fhir.r4.model.Narrative;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.Observation.ObservationStatus;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.Period;
import org.hl7.fhir.r4.model.Quantity;
import org.hl7.fhir.r4.model.Range;
import org.hl7.fhir.r4.model.Ratio;
import org.hl7.fhir.r4.model.Reference;
import org.hl7.fhir.r4.model.StringType;
import org.hl7.fhir.r4.model.StructureDefinition;
import org.hl7.fhir.r4.model.TimeType;
import org.junit.Test;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.DataFormatException;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.parser.IParserErrorHandler;
import ca.uhn.fhir.parser.json.JsonLikeValue.ScalarType;
import ca.uhn.fhir.parser.json.JsonLikeValue.ValueType;

/**
 * @author seanmuir
 *
 */
public class CreateFHIR {

	@Test
	public void testencoide2() throws IOException {
		FhirContext ctx = FhirContext.forR4();
		if (ctx != null) {
			// System.out.println(mdmiMessage.getDataAsString());
			String content = new String(Files.readAllBytes(Paths.get("drlist.xml")));
			IParser parse = ctx.newXmlParser();
			IParserErrorHandler aaa = new IParserErrorHandler() {

				@Override
				public void containedResourceWithNoId(IParseLocation arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void incorrectJsonType(IParseLocation arg0, String arg1, ValueType arg2, ScalarType arg3,
						ValueType arg4, ScalarType arg5) {
					// TODO Auto-generated method stub

				}

				@Override
				public void invalidValue(IParseLocation arg0, String arg1, String arg2) {
					// TODO Auto-generated method stub

				}

				@Override
				public void missingRequiredElement(IParseLocation arg0, String arg1) {
					// TODO Auto-generated method stub

				}

				@Override
				public void unexpectedRepeatingElement(IParseLocation arg0, String arg1) {
					// TODO Auto-generated method stub

				}

				@Override
				public void unknownAttribute(IParseLocation arg0, String arg1) {
					// TODO Auto-generated method stub

				}

				@Override
				public void unknownElement(IParseLocation arg0, String arg1) {
					// TODO Auto-generated method stub

				}

				@Override
				public void unknownReference(IParseLocation arg0, String arg1) {
					// TODO Auto-generated method stub

				}
			};
			// parse.setParserErrorHandler(aaa);
			System.out.println(content);
			IBaseResource bundle = parse.parseResource(content);
			// Bundle bundle = parse.parseResource(Bundle.class, content);

			System.out.println(ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(bundle));
			// mdmiMessage.setData(ctx.newJsonParser().encodeResourceToString(bundle));
		}
	}

	@Test
	public void testencoide() throws IOException {
		FhirContext ctx = FhirContext.forR4();
		if (ctx != null) {
			// System.out.println(mdmiMessage.getDataAsString());
			String content = new String(Files.readAllBytes(Paths.get("temp/boom.xml")));
			IParser parse = ctx.newXmlParser();
			IParserErrorHandler aaa = new IParserErrorHandler() {

				@Override
				public void containedResourceWithNoId(IParseLocation arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void incorrectJsonType(IParseLocation arg0, String arg1, ValueType arg2, ScalarType arg3,
						ValueType arg4, ScalarType arg5) {
					// TODO Auto-generated method stub

				}

				@Override
				public void invalidValue(IParseLocation arg0, String arg1, String arg2) {
					// TODO Auto-generated method stub

				}

				@Override
				public void missingRequiredElement(IParseLocation arg0, String arg1) {
					// TODO Auto-generated method stub

				}

				@Override
				public void unexpectedRepeatingElement(IParseLocation arg0, String arg1) {
					// TODO Auto-generated method stub

				}

				@Override
				public void unknownAttribute(IParseLocation arg0, String arg1) {
					// TODO Auto-generated method stub

				}

				@Override
				public void unknownElement(IParseLocation arg0, String arg1) {
					// TODO Auto-generated method stub

				}

				@Override
				public void unknownReference(IParseLocation arg0, String arg1) {
					// TODO Auto-generated method stub

				}
			};
			// parse.setParserErrorHandler(aaa);
			System.out.println(content);
			IBaseResource bundle = parse.parseResource(content);
			// Bundle bundle = parse.parseResource(Bundle.class, content);

			System.out.println(ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(bundle));
			// mdmiMessage.setData(ctx.newJsonParser().encodeResourceToString(bundle));
		}
	}

	@Test
	public void compare() {
		FhirContext ctx = FhirContext.forR4();
		StructureDefinition sd = new StructureDefinition();

		for (ElementDefinition ed : sd.getDifferential().getElement()) {

			ed.getSliceName();
			ed.getBinding();

		}

		// sd.comp

	}

	@Test
	public void compare2() {

		DiffReport postOne = new DiffReport();
		postOne.setTitle("First Post");
		postOne.setContent("This is my first post");
		postOne.setExcerpt("My Blog Post");

		DiffReport postTwo = new DiffReport();
		postTwo.setTitle("Second Post");
		postTwo.setContent("This is my second post");
		postTwo.setExcerpt("My Blog Post");

		DiffResult diff = postOne.diff(postTwo);

		for (Diff<?> d : diff.getDiffs()) {
			System.out.println(d.getFieldName() + "= FROM[" + d.getLeft() + "] TO [" + d.getRight() + "]");
		}

		System.out.println();
	}

	@Test
	public void test() throws DataFormatException, IOException {

		org.apache.commons.logging.Log kkk;

		FhirContext ctx = FhirContext.forR4();
		Bundle bundle = new Bundle();

		bundle.setType(BundleType.SEARCHSET);

		Patient pat = new Patient();

		pat.addName().addGiven("First").setFamily("Family");
		pat.addAddress().setCity("Mechanicsburg").setState("PA");

		bundle.addEntry().setResource(pat);

		// bundle.addChild("meta");

		bundle.addEntry().setResource(AddBundle("LABS"));
		// bundle.addEntry().setResource(AddBundle("MEDICATIONS"));
		// bundle.addEntry().setResource(AddBundle("ALLEGIES"));

		Files.write(
			Paths.get("portalfhirbundle.json"),
			ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(bundle).getBytes());

		Files.write(
			Paths.get("boom2.xml"), ctx.newXmlParser().setPrettyPrint(true).encodeResourceToString(bundle).getBytes());

		// System.out.println();

	}

	public Bundle AddBundle(String type) throws IOException {

		TreeWalker asdf;

		Bundle bundle2 = new Bundle();
		bundle2.setType(BundleType.COLLECTION);

		Meta ddd = new Meta();
		;
		ddd.setSource(type);

		bundle2.setMeta(ddd);

		Basic sss = new Basic();
		Narrative narrative = new Narrative();

		String content = new String(Files.readAllBytes(Paths.get("narrative.xml")));

		narrative.setDivAsString(content);

		Meta ddd1 = new Meta();
		;
		ddd1.setSource("Consolidated Narrative for " + type);

		sss.setMeta(ddd1);

		sss.setText(narrative);

		bundle2.addEntry().setResource(sss);

		// String[] ids = { "11111111", "22222222", "3333333", "4444444" };

		addReport(bundle2, createAList(10));
		next = 12;
		addReport(bundle2, createAList(1));

		// String[] id2s = { "AAAAAAA", "BBBBBBB", "CCCC", "DDDDD" };
		// addReport(bundle2, createAList(10));

		// String[] id3s = { "1212121212", "343434343", "5555555", "fffffffff" };
		// addReport(bundle2, createAList(10));

		return bundle2;
	}

	ArrayList<String> createAList(int size) {
		ArrayList<String> ids = new ArrayList<String>();

		for (int i = 0; i < size; i++) {
			int length = 15;
			boolean useLetters = true;
			boolean useNumbers = true;
			String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);
			ids.add(generatedString.toUpperCase());
		}
		return ids;
	}

	void addReport(Bundle bundle2, ArrayList<String> arrayList) throws IOException {
		bundle2.addEntry().setResource(addDR(arrayList));

		for (String id : arrayList) {
			bundle2.addEntry().setResource(addObservation(id));
		}

	}

	DiagnosticReport addDR(ArrayList<String> arrayList) {
		DiagnosticReport dr = new DiagnosticReport();

		int length = 10;
		boolean useLetters = true;
		boolean useNumbers = true;
		String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);

		dr.setId(generatedString.toUpperCase());

		CodeableConcept value = new CodeableConcept();
		value.setText("Complete Blood Count");

		dr.setCode(value);

		StringType code = new StringType();
		code.setValue("PAGE1");
		dr.addExtension().setUrl("khie/page").setValue(code);

		//

		DateTimeType dt = new DateTimeType();

		dt.setValueAsString("2011-03-04T08:30:00+11:00");

		dr.setEffective(dt);

		dr.addPerformer().setId("ACME");

		for (String id : arrayList) {
			dr.addResult().setId(id);
		}
		return dr;
	}

	/*
	 * if (value != null && !(value instanceof Quantity || value instanceof CodeableConcept || value instanceof StringType || value instanceof BooleanType
	 * || value instanceof IntegerType || value instanceof Range || value instanceof Ratio || value instanceof SampledData || value instanceof TimeType ||
	 * value instanceof DateTimeType || value instanceof Period))
	 *
	 */

	static int next = 0;

	Observation addObservation(String id) throws IOException {
		Observation observation = new Observation();

		observation.addReferenceRange().setText("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

		observation.addIdentifier().setValue(id);

		/*
		 * "code": "718-7",
		 * "display": "Hemoglobin [Mass/volume] in Blood"
		 */

		int length = 4;
		boolean useLetters = false;
		boolean useNumbers = true;
		String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);
		observation.setStatus(ObservationStatus.FINAL);
		observation.getCode().addCoding().setSystem("http://loinc.org").setCode(generatedString).setDisplay(
			RandomStringUtils.random(25, true, false));

		switch (next++) {
			case 0:

				double randomDouble = Math.random();
				randomDouble = randomDouble * 50 + 1;
				int randomInt = (int) randomDouble;
				// System.out.println(randomInt);

				observation.setValue(
					new Quantity().setValue(randomInt).setUnit("g/L").setSystem("http://unitsofmeasure.org").setCode(
						"g/L"));

				observation.addReferenceRange().setLow(new Quantity(135)).setHigh(new Quantity(180));
				break;
			case 1:
				CodeableConcept cc = new CodeableConcept();
				cc.addCoding().setCode(RandomStringUtils.random(25, true, true)).setDisplay(
					RandomStringUtils.random(25, true, false)).setSystem(RandomStringUtils.random(25, true, false));
				observation.setValue(cc);
				break;

			case 12:
				StringType st = new StringType();
				String content = new String(Files.readAllBytes(Paths.get("r.txt")));
				st.setValue(content);
				observation.setValue(st);
				break;
			case 3:
				BooleanType bt = new BooleanType();
				bt.setValue(Boolean.TRUE);
				observation.setValue(bt);
				break;
			case 4:
				IntegerType it = new IntegerType();
				it.setValue(100);
				observation.setValue(it);
				break;
			case 5:
				Range range = new Range();
				range.setLow(new Quantity(135));
				range.setHigh(new Quantity(145));
				observation.setValue(range);
				break;
			case 6:
				Ratio ratio = new Ratio();
				ratio.setDenominator(new Quantity(135));
				ratio.setNumerator(new Quantity(135));
				observation.setValue(ratio);
				break;
			case 7:
				TimeType tt = new TimeType();
				tt.setValue("12");
				observation.setValue(tt);
				break;
			case 8:
				DateTimeType dtt = new DateTimeType();
				dtt.setValue(new Date());
				observation.setValue(dtt);
				break;
			case 9:
				Period p = new Period();
				p.setEnd(new Date());
				p.setStart(new Date());
				observation.setValue(p);
				break;
		}
		if (next > 9) {
			next = 0;
		}

		observation.addInterpretation().setText("N");

		return observation;

	}

	@Test
	public void testDR() throws DataFormatException, IOException {

		org.apache.commons.logging.Log kkk;

		ArrayList<String> docids = createAList(10);
		ArrayList<String> patientid = createAList(1);
		ArrayList<String> authors = createAList(10);

		FhirContext ctx = FhirContext.forR4();
		Bundle bundle = new Bundle();
		bundle.setType(BundleType.SEARCHSET);

		int counter = 0;
		for (String docid : docids) {

			DocumentReference dr = new DocumentReference();

			CodeableConcept aaa = new CodeableConcept();
			aaa.setText("Lab Report");
			aaa.addCoding().setCode("loinc");
			dr.setType(aaa);

			dr.setIdBase(docid);

			Identifier mi = new Identifier();
			mi.setId(docid);
			dr.setMasterIdentifier(mi);

			dr.setDocStatus(ReferredDocumentStatus.FINAL);

			Reference rfffr = new Reference();
			rfffr.setId(patientid.get(0));
			dr.setSubject(rfffr);

			rfffr = new Reference();
			rfffr.setId(authors.get(counter));
			rfffr.setDisplay("custodian name");
			dr.setCustodian(rfffr);

			rfffr = new Reference();
			rfffr.setId(authors.get(counter));
			rfffr.setDisplay("author name");
			dr.getAuthor().add(rfffr);

			// dr.
			// dr.get

			// DocumentReferenceStatus DocumentReferenceStatus.CURRENT;
			dr.setStatus(DocumentReferenceStatus.CURRENT);

			InstantType iii = new InstantType();
			iii.setValue(new Date());
			dr.setDateElement(iii);

			DocumentReferenceContextComponent drcc = new DocumentReferenceContextComponent();
			;

			Period p = new Period();
			;
			p.setStart(new Date());
			p.setEnd(new Date());
			drcc.setPeriod(p);
			dr.setContext(drcc);

			drcc.addEvent().addCoding().setCode("eventtypecode");

			CodeableConcept ccc = new CodeableConcept();

			ccc.addCoding().setCode("facilitytypecode");
			drcc.setFacilityType(ccc);

			bundle.addEntry().setResource(dr);
			counter++;
		}

		// System.out.println(ctx.newXmlParser().setPrettyPrint(true).encodeResourceToString(bundle).getBytes());
		Files.write(

			Paths.get("drlist.json"),
			ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(bundle).getBytes());

		Files.write(

			Paths.get("drlist.xml"), ctx.newXmlParser().setPrettyPrint(true).encodeResourceToString(bundle).getBytes());

	}

}
