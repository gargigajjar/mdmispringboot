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
package org.mdmi.rt.service.web.fhir;

import org.hl7.fhir.instance.model.api.IBaseResource;
import org.mdmi.MessageModel;
import org.mdmi.core.MdmiMessage;
import org.mdmi.core.engine.preprocessors.IPreProcessor;

import ca.uhn.fhir.context.FhirContext;

/**
 * @author seanmuir
 *
 */
public class PreProcessorForFHIRJson implements IPreProcessor {

	/*
	 * (non-Javadoc)
	 *
	 * @see org.mdmi.core.engine.preprocessors.IPreProcessor#canProcess(org.mdmi.MessageModel)
	 */
	@Override
	public boolean canProcess(MessageModel messageModel) {
		if ("STU3JSON".equals(messageModel.getGroup().getName())) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.mdmi.core.engine.preprocessors.IPreProcessor#getName()
	 */
	@Override
	public String getName() {
		return "PreProcessorForFHIRJson";
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.mdmi.core.engine.preprocessors.IPreProcessor#processMessage(org.mdmi.MessageModel, org.mdmi.core.MdmiMessage)
	 */
	@Override
	public void processMessage(MessageModel messageModel, MdmiMessage mdmiMessage) {
		FhirContext ctx = FhirContext.forDstu3();
		if (ctx != null) {
			System.out.println(mdmiMessage.getDataAsString());

			// Bundle bundle = ctx.newJsonParser().parseResource(Bundle.class, mdmiMessage.getDataAsString());

			IBaseResource b = ctx.newJsonParser().parseResource(mdmiMessage.getDataAsString());

			System.out.println(ctx.newXmlParser().encodeResourceToString(b));

			mdmiMessage.setData(ctx.newXmlParser().encodeResourceToString(b));
		}
	}

}
