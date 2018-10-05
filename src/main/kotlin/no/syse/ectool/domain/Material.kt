package no.syse.ectool.domain

import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.getValue
import tornadofx.setValue

class Material {
    val idProperty = SimpleIntegerProperty()
    var id by idProperty

    val descriptionProperty = SimpleStringProperty()
    var description by descriptionProperty

    val commentProperty = SimpleStringProperty()
    var comment by commentProperty

    val familyProperty = SimpleStringProperty()
    var family by familyProperty
}