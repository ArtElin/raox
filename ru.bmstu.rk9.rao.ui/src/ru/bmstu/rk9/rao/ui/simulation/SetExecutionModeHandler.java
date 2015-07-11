package ru.bmstu.rk9.rao.ui.simulation;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.handlers.RadioState;

public class SetExecutionModeHandler extends AbstractHandler {
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		if (HandlerUtil.matchesRadioState(event))
			return null;

		String currentState = event.getParameter(RadioState.PARAMETER_ID);

		HandlerUtil.updateRadioState(event.getCommand(), currentState);

		SimulationModeDispatcher.setMode(currentState);

		return null;
	}
}
