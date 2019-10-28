package de.achimmihca.lineshortcuts.commands;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.IHandlerService;

import de.achimmihca.lineshortcuts.logging.LogWrapper;

/**
 * Re-opens the last closed editor.
 */
public class CutLineOrSelectedTextHandler extends AbstractEditorTextSelectionHandler {

	private static LogWrapper log = new LogWrapper( CutLineOrSelectedTextHandler.class );

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IHandlerService handlerService = (IHandlerService) PlatformUI.getWorkbench().getService( IHandlerService.class );

		// Get the current text editor
		var textEditor = getActiveTextEditor();
		if( textEditor == null ) {
			// Fall back to default cut handler
			return executeDefaultCutHandler( handlerService );
		}
		// Get the current selection
		var textSelection = getTextSelection( textEditor );
		if( textSelection == null ) {
			return null;
		}

		// Cut the line when the selection is empty. Otherwise, cut the selected text.
		if( textSelection.getLength() == 0 ) {
			return cutLine( handlerService );
		} else {
			return cutSelectedText( handlerService );
		}
	}

	private Object executeDefaultCutHandler(IHandlerService handlerService) {
		try {
			return handlerService.executeCommand( "org.eclipse.ui.edit.cut", null );
		} catch( Exception e ) {
			log.error( e );
			return null;
		}
	}

	private Object cutSelectedText(IHandlerService handlerService) {
		return executeDefaultCutHandler( handlerService );
	}

	private Object cutLine(IHandlerService handlerService) {
		try {
			return handlerService.executeCommand( "org.eclipse.ui.edit.text.cut.line", null );
		} catch( Exception e ) {
			log.error( e );
			return null;
		}
	}
}