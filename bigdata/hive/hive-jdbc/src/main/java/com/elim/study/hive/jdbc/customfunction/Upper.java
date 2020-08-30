package com.elim.study.hive.jdbc.customfunction;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;

public class Upper extends GenericUDF {

    public ObjectInspector initialize(ObjectInspector[] arguments) throws UDFArgumentException {
        return null;
    }

    public Object evaluate(DeferredObject[] arguments) throws HiveException {
        return null;
    }

    public String getDisplayString(String[] children) {
        return null;
    }

}
