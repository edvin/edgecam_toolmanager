package no.syse.ectool.domain

import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class Material {
    val idProperty = SimpleIntegerProperty()
    var id by idProperty

    val descriptionProperty = SimpleStringProperty()
    var description by descriptionProperty

    val commentProperty = SimpleStringProperty()
    var comment by commentProperty

    val familyProperty = SimpleStringProperty()
    var family by familyProperty

    override fun toString() = description
}

class MaterialModel : ItemViewModel<Material>() {
    val id = bind(Material::idProperty)
    val description = bind(Material::descriptionProperty)
    val comment = bind(Material::commentProperty)
    val family = bind(Material::familyProperty)
}
