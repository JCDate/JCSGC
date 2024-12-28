package Paginacion;

import javax.swing.JButton;

public interface PaginacionItemRender {

    public JButton createPaginationItem(Object value, boolean isPrevious, boolean isNext, boolean enable);

    public JButton createButton(Object value, boolean isPrevious, boolean isNext, boolean enable);

    public Object createPreviousIcon();

    public Object createNextIcon();

}
