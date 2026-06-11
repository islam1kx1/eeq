module ru.zelmex.salahovcourseach {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires java.desktop;
    requires org.hibernate.validator;
    requires org.postgresql.jdbc;
    requires jakarta.validation;

    opens ru.zelmex.salahovcourseach to javafx.fxml;
    opens ru.zelmex.salahovcourseach.model to org.hibernate.orm.core, javafx.base;
    opens ru.zelmex.salahovcourseach.util to org.hibernate.orm.core;

    exports ru.zelmex.salahovcourseach;
    exports ru.zelmex.salahovcourseach.controller.ModelLines;
    opens ru.zelmex.salahovcourseach.controller.ModelLines to javafx.fxml;
    exports ru.zelmex.salahovcourseach.controller.Dealers;
    opens ru.zelmex.salahovcourseach.controller.Dealers to javafx.fxml;
    exports ru.zelmex.salahovcourseach.controller.Shipments;
    opens ru.zelmex.salahovcourseach.controller.Shipments to javafx.fxml;
}