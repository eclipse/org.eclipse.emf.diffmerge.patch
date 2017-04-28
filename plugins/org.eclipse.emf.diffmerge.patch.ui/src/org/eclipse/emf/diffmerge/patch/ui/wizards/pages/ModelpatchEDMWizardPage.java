/*******************************************************************************
 * Copyright (c) 2016-2017 Thales Global Services S.A.S.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Abel Hegedus, Tamas Borbas, Peter Lunk (IncQuery Labs Ltd.) - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.diffmerge.patch.ui.wizards.pages;

import org.eclipse.compare.CompareEditorInput;
import org.eclipse.emf.diffmerge.patch.ui.utils.ModelpatchApplicationDTO;
import org.eclipse.emf.diffmerge.ui.EMFDiffMergeUIPlugin;
import org.eclipse.emf.diffmerge.ui.viewers.ComparisonViewer;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

public class ModelpatchEDMWizardPage extends WizardPage {
  ModelpatchApplicationDTO dto;

  ComparisonViewer comp;

  /**
   * Create the wizard.
   */
  public ModelpatchEDMWizardPage(ModelpatchApplicationDTO dto) {
    super("EDM Wizard Page");
    setTitle("Differences");
    setDescription(
        "You can select changes which you want to apply. The target model will be only modified if you explicitly merge changes from the synthesis panel.");
    this.dto = dto;
  }

  /**
   * Create contents of the wizard.
   *
   * @param parent
   */
  public void createControl(Composite parent) {
    Composite container = new Composite(parent, SWT.NULL);

    setControl(container);
    container.setLayout(new GridLayout(1, false));

    // AbstractComparisonViewer runs into NPE otherwise (and causes WindowBuilder parse error)
    if(EMFDiffMergeUIPlugin.getDefault() != null) {
      comp = new ComparisonViewer(container) {
        @Override
        protected void setupToolBars() {
          super.setupToolBars();
          addPropertyChangeListener(new IPropertyChangeListener() {
            /**
             * @see org.eclipse.jface.util.IPropertyChangeListener#propertyChange(org.eclipse.jface.util.PropertyChangeEvent)
             */
            public void propertyChange(PropertyChangeEvent event_p) {
              if (CompareEditorInput.DIRTY_STATE.equals(event_p.getProperty()) && event_p.getNewValue() instanceof Boolean
                  && ((Boolean) event_p.getNewValue()).booleanValue()) {
                setPageComplete(true);
              }
            }
          });
        }

      };
      comp.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
    }
    setPageComplete(false);
  }

  @Override
  public void setVisible(boolean visible) {
    if (visible) {
      dto.prepareDiffNode();
      dto.diffNode.updateDifferenceNumbers();
      comp.setInput(dto.diffNode);
    }
    super.setVisible(visible);
  }

  @Override
  public IWizardPage getPreviousPage() {
    return null;
  }

}
