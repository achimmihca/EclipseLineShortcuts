package de.achimmihca.lineshortcuts.commands;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.IHandlerService;

import de.achimmihca.lineshortcuts.logging.LogWrapper;

/**
 * Re-opens the last closed editor.
 */
public class CopyLineOrSelectedTextHandler extends AbstractEditorTextSelectionHandler {

	private static LogWrapper log = new LogWrapper( CopyLineOrSelectedTextHandler.class );

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IHandlerService handlerService = (IHandlerService) PlatformUI.getWorkbench().getService( IHandlerService.class );

		// Get the current text editor
		var textEditor = getActiveTextEditor();
		if( textEditor == null ) {
			// Fall back to default copy handler
			return executeDefaultCopyHandler( handlerService );
		}
		// Get the current selection
		var textSelection = getTextSelection( textEditor );
		if( textSelection == null ) {
			return null;
		}

		// Copy the line when the selection is empty. Otherwise, copy the selected text.
		if( textSelection.getLength() == 0 ) {
			return copyLine( handlerService );
		} else {
			return copySelectedText( handlerService );
		}
	}

	private Object executeDefaultCopyHandler(IHandlerService handlerService) {
		try {
			return handlerService.executeCommand( "org.eclipse.ui.edit.copy", null );
		} catch( Exception e ) {
			log.error( e );
			return null;
		}
	}

	private Object copySelectedText(IHandlerService handlerService) {
		return executeDefaultCopyHandler( handlerService );
	}

	private Object copyLine(IHandlerService handlerService) {
		// First select the complete line, afterwards copy the selected text.
		try {
			handlerService.executeCommand( "org.eclipse.ui.edit.text.goto.lineStart", null );
			handlerService.executeCommand( "org.eclipse.ui.edit.text.select.lineEnd", null );
		} catch( Exception e ) {
			log.error( "Exception in attempt to copy the complete line. Falling back to default copy handler", e );
			return executeDefaultCopyHandler( handlerService );
		}
		return copySelectedText( handlerService );
	}

}