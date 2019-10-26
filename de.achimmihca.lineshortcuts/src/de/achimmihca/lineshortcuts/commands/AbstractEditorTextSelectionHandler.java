package de.achimmihca.lineshortcuts.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.editors.text.TextEditor;

abstract public class AbstractEditorTextSelectionHandler extends AbstractHandler {

	protected TextEditor getActiveTextEditor() {
		var workbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if( workbenchWindow == null ) {
			return null;
		}
		var workbenchPage = workbenchWindow.getActivePage();
		if( workbenchPage == null ) {
			return null;
		}
		var activeEditor = workbenchPage.getActiveEditor();
		if( !( activeEditor instanceof TextEditor ) ) {
			return null;
		}
		var textEditor = (TextEditor) activeEditor;
		return textEditor;
	}

	protected TextSelection getTextSelection(TextEditor textEditor) {
		var selection = textEditor.getSelectionProvider().getSelection();
		if( !( selection instanceof TextSelection ) ) {
			return null;
		}
		var textSelection = (TextSelection) selection;
		return textSelection;
	}
}
