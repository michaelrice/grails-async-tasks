package com.budjb.asynchronoustasks

abstract class AbstractAsynchronousTask implements AsynchronousTask {
    /**
     * Starts a task.
     */
    abstract protected void start()

    /**
     * Starts a task.
     *
     * @param description Description of the current step in the overall process of the task.
     */
    abstract protected void start(String description)

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
     */
    abstract protected void error()

    /**
     * Sets the task in an error state.
     *
     * @param results
     */
    abstract protected void error(Object results)

    /**
     * Sets the task in a failure state.
     */
    abstract protected void failure()

    /**
     * Sets the task in a failure state.
     *
     * @param results
     */
    abstract protected void failure(Object results)

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
}
