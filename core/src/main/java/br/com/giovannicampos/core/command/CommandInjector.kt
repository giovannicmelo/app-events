package br.com.giovannicampos.core.command

import br.com.giovannicampos.core.command.models.GenericCommand
import br.com.giovannicampos.core.command.interfaces.CommandProvider
import br.com.giovannicampos.core.ui.SingleLiveEvent

object CommandInjector :
    CommandProvider {

    override fun getCommand(): SingleLiveEvent<GenericCommand> {

        return SingleLiveEvent()
    }
}