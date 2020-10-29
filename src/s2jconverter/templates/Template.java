/*
 * Copyright (C) 2020 Sicut
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package s2jconverter.templates;

import java.util.Scanner;

/**
 *
 * @author Sicut
 */
public class Template {

    public Template() {
    }
       
    public String getTplFile(String file) {
        return new Scanner(getClass().getResourceAsStream(file), "UTF-8").useDelimiter("\\A").next();
    }
    
    public String getVarType(String rawType) {
        if ( rawType.matches("INTEGER|INT|TINYINT|SMALLINT|MEDIUMINT|INT2|INT8") )
            return "int";
        if ( rawType.matches("BIGINT|UNSIGNED BIGINT") )
            return "long";
        if ( rawType.matches("((CHARACTER|VARCHAR|CHARACTER|NCHAR|NVARCHAR)\\([0-9]+\\))|TEXT|CLOB|VARYING|NATIVE") )
            return "String";
        if ( rawType.matches("REAL|DOUBLE|FLOAT") )
            return "double";
        return "String";
    }
}
