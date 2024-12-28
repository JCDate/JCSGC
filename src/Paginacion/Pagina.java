package Paginacion;

import java.util.List;

public class Pagina {

    private int current;
    private boolean previous;
    private boolean next;
    private List<Object> items;
    private int totalPagina;

    public Pagina() {
    }

    public Pagina(int current, boolean previous, boolean next, List<Object> items, int totalPagina) {
        this.current = current;
        this.previous = previous;
        this.next = next;
        this.items = items;
        this.totalPagina = totalPagina;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public boolean isPrevious() {
        return previous;
    }

    public void setPrevious(boolean previous) {
        this.previous = previous;
    }

    public boolean isNext() {
        return next;
    }

    public void setNext(boolean next) {
        this.next = next;
    }

    public List<Object> getItems() {
        return items;
    }

    public void setItems(List<Object> items) {
        this.items = items;
    }

    public int getTotalPagina() {
        return totalPagina;
    }

    public void setTotalPagina(int totalPagina) {
        this.totalPagina = totalPagina;
    }

    @Override
    public String toString() {
        return "current: " + current + "\n" + previous + " " + items.toString() + " " + next;
    }

    public static class BreakLabel {

        public int getPagina() {
            return page;
        }

        public void setPagina(int page) {
            this.page = page;
        }

        public BreakLabel(int page) {
            this.page = page;
        }

        public BreakLabel() {
        }

        private int page;

        @Override
        public String toString() {
            return "...";
        }
    }
}
