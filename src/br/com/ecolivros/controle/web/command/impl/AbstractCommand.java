package br.com.ecolivros.controle.web.command.impl;

import br.com.ecolivros.controle.web.command.ICommand;
import br.com.ecolivros.core.IFachada;
import br.com.ecolivros.core.impl.controle.Fachada;

public abstract class AbstractCommand implements ICommand {
	protected IFachada fachada = new Fachada();
}