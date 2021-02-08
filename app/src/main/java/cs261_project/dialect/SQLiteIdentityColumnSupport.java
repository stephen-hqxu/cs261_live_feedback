package cs261_project.dialect;

import org.hibernate.MappingException;
import org.hibernate.dialect.identity.IdentityColumnSupportImpl;

/**
 * SQLite table column
 * @author Group 12 - Stephen Xu, JuanYan Huo, Ellen Tatum, JiaQi Lv, Alexander Odewale
 */
public class SQLiteIdentityColumnSupport extends IdentityColumnSupportImpl {

    SQLiteIdentityColumnSupport(){
        super();
    }

    @Override
    public boolean supportsIdentityColumns() {
        return true;
    }

    @Override
    public String getIdentitySelectString(String table, String column, int type) throws MappingException {
        return "select last_insert_rowid()";
    }

    @Override
    public String getIdentityColumnString(int type) throws MappingException {
        return "integer";
    }
}