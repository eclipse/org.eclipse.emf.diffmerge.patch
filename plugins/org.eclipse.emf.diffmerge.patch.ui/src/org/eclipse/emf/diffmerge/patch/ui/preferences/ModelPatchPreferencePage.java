/*******************************************************************************
 * Copyright (c) 2016-2018 Thales Global Services S.A.S.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *      Abel Hegedus, Tamas Borbas, Balazs Grill, Peter Lunk, Daniel Segesdi (IncQuery Labs Ltd.) - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.diffmerge.patch.ui.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.emf.diffmerge.patch.runtime.modelaccess.ModelAccessTypes;
import org.eclipse.emf.diffmerge.patch.ui.ModelPatchUIPlugin;
import org.eclipse.emf.diffmerge.patch.ui.utils.PersistenceTypes;

public class ModelPatchPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

  public ModelPatchPreferencePage() {
    super(GRID);

  }

  public void createFieldEditors() {

    addField(new RadioGroupFieldEditor(ModelPatchPreferenceProvider.MODELACCESS_PREFERENCE_ID, "Model access:", 1,
        new String[][] { { "EMF Reflective Model Access", ModelAccessTypes.EMF_REFLECTIVE.toString() },
            { "Editing Domain Aware Model Access", ModelAccessTypes.VIATRA.toString() } },
        getFieldEditorParent()));

    addField(new RadioGroupFieldEditor(
        ModelPatchPreferenceProvider.SERIALIZER_PREFERENCE_ID, "Serialization:", 1, new String[][] {
            { "GSON - JSON", PersistenceTypes.GSON.toString() }, { "EMF - XMI", PersistenceTypes.EMF.toString() } },
        getFieldEditorParent()));
  }

  @Override
  public void init(IWorkbench workbench) {
    setPreferenceStore(ModelPatchUIPlugin.getDefault().getPreferenceStore());
  }

}
