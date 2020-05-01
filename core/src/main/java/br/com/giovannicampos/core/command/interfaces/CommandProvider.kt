package br.com.giovannicampos.core.command.interfaces

import br.com.giovannicampos.core.command.models.GenericCommand
import br.com.giovannicampos.core.ui.SingleLiveEvent

interface CommandProvider {

    fun getCommand(): SingleLiveEvent<GenericCommand>
}