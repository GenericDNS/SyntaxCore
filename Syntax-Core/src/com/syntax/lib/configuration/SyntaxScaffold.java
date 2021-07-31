package com.syntax.lib.configuration;

import com.syntax.lib.mysql.Adapter;

public interface SyntaxScaffold {

    public void toDataBase(Adapter adapter, String... path);

}
