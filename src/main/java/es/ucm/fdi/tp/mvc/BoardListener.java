package es.ucm.fdi.tp.mvc;

public interface BoardListener {
    /**
     * Notifies the observer of an event. Typically called by a GameObservable
     * that this observer has registered with
     * @param e the event
     */
    void notifyBoardEvent(BoardEvent e);
}