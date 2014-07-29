package com.budjb.asynchronoustasks

public interface AsynchronousTask {
    /**
     * Returns the task ID of the task.
     *
     * @return
     */
    public int getTaskId()

    /**
     * Returns the task name.
     */
    public String getTaskName()

    /**
     * Gets the current progress of the task.
     *
     * @return
     */
    public int getProgress()

    /**
     * Gets the results associated with a task that has ended.
     *
     * @return
     */
    public Object getResults()

    /**
     * Gets the current state of the task.
     *
     * @return
     */
    public AsynchronousTaskState getState()

    /**
     * Gets the time when the task was created.
     *
     * @return
     */
    public Date getCreatedTime()

    /**
     * Gets the time when the task was last updated.
     *
     * @return
     */
    public Date getUpdatedTime()

    /**
     * Gets the time when the task was started.
     *
     * @return
     */
    public Date getStartTime()

    /**
     * Gets the time when the task was ended.
     *
     * @return
     */
    public Date getEndTime()
}
