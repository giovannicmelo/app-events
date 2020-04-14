package br.com.giovannicampos.woopevents.base.command

import br.com.giovannicampos.woopevents.base.command.models.GenericCommand
import br.com.giovannicampos.woopevents.base.command.providers.CommandProvider
import br.com.giovannicampos.woopevents.base.ui.SingleLiveEvent

object CommandInjector : CommandProvider {

    override fun getCommand(): SingleLiveEvent<GenericCommand> {

        return SingleLiveEvent()
    }
}