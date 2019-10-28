package de.achimmihca.lineshortcuts.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.ITextEditor;

abstract public class AbstractEditorTextSelectionHandler extends AbstractHandler {

	protected ITextEditor getActiveTextEditor() {
		var workbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if( workbenchWindow == null ) {
			return null;
		}
		var workbenchPage = workbenchWindow.getActivePage();
		if( workbenchPage == null ) {
			return null;
		}
		// Must use getActivePart here and not getActiveEditor
		// because getActiveEditor will also return an editor when its does not have focus.
		var activePart = workbenchPage.getActivePart();
		if( !( activePart instanceof ITextEditor ) ) {
			return null;
		}
		var textEditor = (ITextEditor) activePart;
		return textEditor;
	}

	protected ITextSelection getTextSelection(ITextEditor textEditor) {
		var selection = textEditor.getSelectionProvider().getSelection();
		if( !( selection instanceof ITextSelection ) ) {
			return null;
		}
		var textSelection = (ITextSelection) selection;
		return textSelection;
	}
}
