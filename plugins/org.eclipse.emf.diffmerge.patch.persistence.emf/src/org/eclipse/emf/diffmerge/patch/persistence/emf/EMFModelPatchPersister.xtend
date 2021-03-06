/*******************************************************************************
 * Copyright (c) 2017-2018 Thales Global Services S.A.S.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Abel Hegedus (IncQuery Labs Ltd.) - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.diffmerge.patch.persistence.emf

import org.eclipse.emf.diffmerge.patch.api.ModelPatch
import org.eclipse.emf.diffmerge.patch.api.ModelPatchException
import org.eclipse.emf.diffmerge.patch.persistence.ModelPatchPersister
import org.eclipse.emf.diffmerge.patch.persistence.emf.emodelpatch.EModelPatch
import org.eclipse.emf.diffmerge.patch.persistence.emf.emodelpatch.EModelPatchFactory
import org.eclipse.emf.diffmerge.patch.persistence.emf.transform.ModelPatchToEMF
import org.apache.log4j.Logger
import org.eclipse.emf.diffmerge.patch.persistence.emf.transform.EModelPatchToPOJO

class EMFModelPatchPersister implements ModelPatchPersister<EModelPatch> {

  extension EModelPatchFactory factory = EModelPatchFactory.eINSTANCE
  val logger = Logger.getLogger(EMFModelPatchPersister)

  override persist(ModelPatch modelPatch) throws ModelPatchException {
    val root = createEModelPatch
    val mp2emf = new ModelPatchToEMF(modelPatch, root, logger, true)
    mp2emf.transform
    return root
  }

  override load(EModelPatch eModelPatch) throws ModelPatchException {
    val root = new ModelPatch
    val mp2pojo = new EModelPatchToPOJO(eModelPatch, root, logger)
    mp2pojo.transform
    return root
  }

}
