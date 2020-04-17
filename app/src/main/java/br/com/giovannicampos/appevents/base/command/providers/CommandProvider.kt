package br.com.giovannicampos.appevents.base.command.providers

import br.com.giovannicampos.appevents.base.command.models.GenericCommand
import br.com.giovannicampos.appevents.base.ui.SingleLiveEvent

interface CommandProvider {

    fun getCommand(): SingleLiveEvent<GenericCommand>
}