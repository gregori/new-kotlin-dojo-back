package tech.gregori.kotlindojoback.config

import org.hibernate.dialect.MariaDBDialect

class MariaDBCustomDialect : MariaDBDialect() {
    override fun getTableTypeString(): String {
        return " ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin"
    }
}