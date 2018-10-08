package no.syse.ectool.events

import no.syse.ectool.domain.Tool
import tornadofx.*

class ToolAddedEvent(val tool: Tool) : FXEvent()