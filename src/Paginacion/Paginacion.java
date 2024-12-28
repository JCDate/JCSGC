package Paginacion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Paginacion extends JPanel {

    public PaginacionItemRender getPaginacionItemRender() {
        return paginationItemRender;
    }

    public void setPaginacionItemRender(PaginacionItemRender paginationItemRender) {
        this.paginationItemRender = paginationItemRender;
        changePagina(page.getCurrent(), page.getTotalPagina());
    }

    private PaginacionItemRender paginationItemRender;
    private List<EventPaginacion> events = new ArrayList<>();
    private Pagina page;

    public Paginacion() {
        init();
    }

    private void init() {
        paginationItemRender = new DefaultPaginationItemRender();
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));
        setPaginagination(1, 1);
    }

    private void runEvent() {
        for (EventPaginacion event : events) {
            event.pageChanged(page.getCurrent());
        }
    }

    private boolean isEnable(Object item) {
        return (item instanceof Pagina.BreakLabel || Integer.valueOf(item.toString()) != page.getCurrent());
    }

    public void addEventPaginacion(EventPaginacion event) {
        events.add(event);
    }

    public void setPaginagination(int current, int totalPagina) {
        if (current > totalPagina) {
            current = totalPagina;
        }
        if (page == null || (page.getCurrent() != current || page.getTotalPagina() != totalPagina)) {
            changePagina(current, totalPagina);
        }
    }

    private void changePagina(int current, int totalPagina) {
        page = paginate(current, totalPagina);
        removeAll();
        refresh();
        JButton cmdPrev = paginationItemRender.createPaginationItem("Previous", true, false, page.isPrevious());
        cmdPrev.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (page.getCurrent() > 1) {
                    setPaginagination(page.getCurrent() - 1, totalPagina);
                    runEvent();
                }
            }
        });
        add(cmdPrev);
        for (Object item : page.getItems()) {
            JButton cmd = paginationItemRender.createPaginationItem(item, false, false, isEnable(item));
            if (item instanceof Integer) {
                if (Integer.valueOf(item.toString()) == page.getCurrent()) {
                    cmd.setSelected(true);
                }
            }
            cmd.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!cmd.isSelected() && item != null) {
                        if (item instanceof Pagina.BreakLabel) {
                            Pagina.BreakLabel pb = (Pagina.BreakLabel) item;
                            setPaginagination(pb.getPagina(), totalPagina);
                        } else {
                            setPaginagination(Integer.valueOf(item.toString()), totalPagina);
                        }
                        runEvent();
                    }
                }
            });
            add(cmd);
        }
        JButton cmdNext = paginationItemRender.createPaginationItem("Next", false, true, page.isNext());
        cmdNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (page.getCurrent() < page.getTotalPagina()) {
                    setPaginagination(page.getCurrent() + 1, totalPagina);
                    runEvent();
                }
            }
        });
        add(cmdNext);
    }

    private void refresh() {
        repaint();
        revalidate();
    }

    private Pagina paginate(int current, int max) {
        boolean prev = current > 1;
        boolean next = current < max;
        List<Object> items = new ArrayList<>();
        items.add(1);
        if (current == 1 && max == 1) {
            return new Pagina(current, prev, next, items, max);
        }
        int r = 2;
        int r1 = current - r;
        int r2 = current + r;
        if (current > 4) {
            items.add(new Pagina.BreakLabel((r1 > 2 ? r1 : 2) - 1));
        }
        for (int i = r1 > 2 ? r1 : 2; i <= Math.min(max, r2); i++) {
            items.add(i);
        }
        if (r2 + 1 < max) {
            items.add(new Pagina.BreakLabel(Integer.valueOf(items.get(items.size() - 1).toString()) + 1));
        }
        if (r2 < max) {
            items.add(max);
        }
        return new Pagina(current, prev, next, items, max);
    }
}
