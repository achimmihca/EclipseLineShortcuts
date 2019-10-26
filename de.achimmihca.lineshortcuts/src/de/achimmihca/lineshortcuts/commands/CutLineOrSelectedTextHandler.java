package de.achimmihca.lineshortcuts.commands;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.handlers.IHandlerService;

import de.achimmihca.lineshortcuts.logging.LogWrapper;

/**
 * Re-opens the last closed editor.
 */
public class CutLineOrSelectedTextHandler extends AbstractEditorTextSelectionHandler {

	private static LogWrapper log = new LogWrapper( CutLineOrSelectedTextHandler.class );

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

		// Cut the line when the selection is empty. Otherwise, cut the selected text.
		IHandlerService handlerService = (IHandlerService) textEditor.getSite().getService( IHandlerService.class );
		if( textSelection.getLength() == 0 ) {
			cutLine( handlerService );
		} else {
			cutSelectedText( handlerService );
		}
		return null;
	}

	private void cutSelectedText(IHandlerService handlerService) {
		try {
			handlerService.executeCommand( "org.eclipse.ui.edit.cut", null );
		} catch( Exception e ) {
			log.error( e );
		}
	}

	private void cutLine(IHandlerService handlerService) {
		try {
			handlerService.executeCommand( "org.eclipse.ui.edit.text.cut.line", null );
		} catch( Exception e ) {
			log.error( e );
		}
	}
}