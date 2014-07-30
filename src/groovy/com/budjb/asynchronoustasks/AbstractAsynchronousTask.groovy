package com.budjb.asynchronoustasks

abstract class AbstractAsynchronousTask implements AsynchronousTask {
    /**
     * Starts a task.
     */
    abstract protected void start()

    /**
     * Updates the progress of the task.
     *
     * @param progress Task's percentage complete.
     */
    abstract protected void update(int progress)

    /**
     * Updates the progress of the task.
     *
     * @param progress Task's percentage complete.
     * @param description Description of the current step in the overall process of the task.
     */
    abstract protected void update(int progress, String description)

    /**
     * Sets the task in an error state.
     *
     * @param errorCode Error code associated with a failed task.
     */
    abstract protected void error(String errorCode)

    /**
     * Sets the task in an error state.
     *
     * @param errorCode Error code associated with a failed task.
     * @param results
     */
    abstract protected void error(String errorCode, Object results)

    /**
     * Sets the task in a failure state.
     *
     * @param errorCode Error code associated with a failed task.
     */
    abstract protected void failure(String errorCode)

    /**
     * Sets the task in a failure state.
     *
     * @param errorCode Error code associated with a failed task.
     * @param results
     */
    abstract protected void failure(String errorCode, Object results)

    /**
     * Completes the task.
     */
    abstract protected void complete()

    /**
     * Completes the task.
     *
     * @param results
     */
    abstract protected void complete(Object results)

    /**
     * Does the actual work of the task.
     */
    abstract protected void process()

    /**
     * Marks the task as started and starts actual processing.
     */
    @Override
    public void fire() {
        start()
        process()
    }
}
