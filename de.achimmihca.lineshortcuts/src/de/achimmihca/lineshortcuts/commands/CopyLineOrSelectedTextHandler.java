package de.achimmihca.lineshortcuts.commands;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.handlers.IHandlerService;

import de.achimmihca.lineshortcuts.logging.LogWrapper;

/**
 * Re-opens the last closed editor.
 */
public class CopyLineOrSelectedTextHandler extends AbstractEditorTextSelectionHandler {

	private static LogWrapper log = new LogWrapper( CopyLineOrSelectedTextHandler.class );

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// Get the current text editor
		var textEditor = getActiveTextEditor();
		if( textEditor == null ) {
			return null;
		}
		// Get the current selection
		var textSelection = getTextSelection( textEditor );
		if( textSelection == null ) {
			return null;
		}

		// Copy the line when the selection is empty. Otherwise, copy the selected text.
		IHandlerService handlerService = (IHandlerService) textEditor.getSite().getService( IHandlerService.class );
		if( textSelection.getLength() == 0 ) {
			copyLine( handlerService );
		} else {
			copySelectedText( handlerService );
		}
		return null;
	}

	private void copySelectedText(IHandlerService handlerService) {
		try {
			handlerService.executeCommand( "org.eclipse.ui.edit.copy", null );
		} catch( Exception e ) {
			log.error( e );
		}
	}

	private void copyLine(IHandlerService handlerService) {
		try {
			handlerService.executeCommand( "org.eclipse.ui.edit.text.goto.lineStart", null );
			handlerService.executeCommand( "org.eclipse.ui.edit.text.select.lineEnd", null );
			handlerService.executeCommand( "org.eclipse.ui.edit.copy", null );
		} catch( Exception e ) {
			log.error( e );
		}
	}

}