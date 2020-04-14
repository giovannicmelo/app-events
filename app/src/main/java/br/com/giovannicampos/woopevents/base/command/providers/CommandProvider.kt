package br.com.giovannicampos.woopevents.base.command.providers

import br.com.giovannicampos.woopevents.base.command.models.GenericCommand
import br.com.giovannicampos.woopevents.base.ui.SingleLiveEvent

interface CommandProvider {

    fun getCommand(): SingleLiveEvent<GenericCommand>
}