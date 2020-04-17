package br.com.giovannicampos.appevents.base.command

import br.com.giovannicampos.appevents.base.command.models.GenericCommand
import br.com.giovannicampos.appevents.base.command.providers.CommandProvider
import br.com.giovannicampos.appevents.base.ui.SingleLiveEvent

object CommandInjector : CommandProvider {

    override fun getCommand(): SingleLiveEvent<GenericCommand> {

        return SingleLiveEvent()
    }
}