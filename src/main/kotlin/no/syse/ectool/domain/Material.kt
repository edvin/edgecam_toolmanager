package no.syse.ectool.domain

import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import javafx.util.StringConverter
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Material

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

}

class MaterialModel : ItemViewModel<Material>() {
    val id = bind(Material::idProperty)
    val description = bind(Material::descriptionProperty)
    val comment = bind(Material::commentProperty)
    val family = bind(Material::familyProperty)
}
