package org.anthony.library.service_layer.model;

import org.anthony.library.repository.entity.TitleEntity;
import org.anthony.tablePrinter.TablePrinter.Column;

public class Title extends TitleEntity
{
    public Title(TitleEntity title)
    {
        super(title.getIsbn(), title.getTitle());
    }

    public Title(Integer isbn, String title) {
        super(isbn, title);
    }

    @Column(name="isbn", weight=1, width=20)
    @Override
    public Integer getIsbn() {
        return super.getIsbn();
    }

    @Column(name="title", weight=2, width=20)
    public String getTitle()
    {
        return super.getTitle();
    }
}
