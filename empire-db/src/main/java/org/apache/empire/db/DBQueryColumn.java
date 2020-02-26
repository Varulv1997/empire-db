/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.empire.db;

import org.apache.empire.commons.Options;
import org.apache.empire.data.Column;
import org.apache.empire.data.DataType;
import org.w3c.dom.Element;

public class DBQueryColumn extends DBColumn
{
    private final static long serialVersionUID = 1L;
    
    protected final DBColumnExpr expr;

    /**
     * Constructs a DBQueryColumn object set the specified parameters to this object.
     * <P>
     * @param query the DBQuery object
     * @param expr the concrete DBColumnExpr object
     */
    public DBQueryColumn(DBQuery query, String name, DBColumnExpr expr)
    { // call base
        super(query, name);
        // set Expression
        this.expr = expr;
    }
    
    public DBColumnExpr getExpr()
    {
        return expr;
    }

    @Override
    public DataType getDataType()
    {
        return expr.getDataType();
    }

    @Override
    public double getSize()
    {
        DBColumn column = expr.getUpdateColumn();
        if (column==null)
            return 0.0;
        return column.getSize();
    }

    @Override
    public boolean isReadOnly()
    {
        DBColumn column = expr.getUpdateColumn();
        if (column==null)
            return true;
        return column.isReadOnly();
    }

    @Override
    public boolean isAutoGenerated()
    {
        DBColumn column = expr.getUpdateColumn();
        if (column==null)
            return false;
        return column.isAutoGenerated();
    }

    @Override
    public boolean isRequired()
    {
        DBColumn column = expr.getUpdateColumn();
        if (column==null)
            return false;
        return column.isRequired();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Class<Enum<?>> getEnumType()
    {
        // check expression attribute
        Object enumType = expr.getAttribute(Column.COLATTR_ENUMTYPE);
        if (enumType!=null)
            return ((Class<Enum<?>>)enumType);        
        // otherwise check update column
        DBColumn col = expr.getUpdateColumn();
        if (col!=null)
            return col.getEnumType();
        // otherwise 
        return super.getEnumType();
    }

    @Override
    public Object validate(Object value)
    {
        DBColumn column = expr.getUpdateColumn();
        if (column==null)
            return value;
        return column.validate(value);
    }

    @Override
    public Object getAttribute(String name)
    {
        if (attributes != null && attributes.contains(name))
            return attributes.get(name);
        // Otherwise ask expression
        DBColumn column = expr.getUpdateColumn();
        if (column==null)
            return null;
        return column.getAttribute(name);
    }

    @Override
    public Options getOptions()
    {
        if (options != null)
            return options;
        // Otherwise ask expression
        DBColumn column = expr.getUpdateColumn();
        if (column==null)
            return null;
        return column.getOptions();
    }
    
    @Override
    public Element addXml(Element parent, long flags)
    {
        return expr.addXml(parent, flags);
    }
}
