package BotonesAccion;

public interface TableActionEvent {

    void onEdit(int row);

    void onDelete(int row);

    void onView(int row);

    void onOpenRecords(int row);

    void onAccept(int row);

    void onReject(int row);
}
