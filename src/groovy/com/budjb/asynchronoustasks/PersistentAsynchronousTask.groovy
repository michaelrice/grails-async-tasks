package com.budjb.asynchronoustasks

import com.budjb.asynchronoustasks.exception.PersistentAsynchronousTaskLoadException
import com.budjb.asynchronoustasks.exception.PersistentAsynchronousTaskNotFoundException
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper

abstract class PersistentAsynchronousTask extends AbstractAsynchronousTask {
    /**
     * Task domain associated with the given task.
     */
    private AsynchronousTaskDomain task

    /**
     * Creates a brand new task instance.
     */
    public PersistentAsynchronousTask() {
        AsynchronousTaskDomain task = new AsynchronousTaskDomain()
        task.name = getTaskName()
        task.save(flush: true, failOnError: true)
        this.task = task
    }

    /**
     * Loads an existing task.
     *
     * @param taskId
     */
    public PersistentAsynchronousTask(int taskId) {
        // Load the task
        try {
            task = AsynchronousTaskDomain.get(taskId)
        }
        catch (Exception e) {
            throw new PersistentAsynchronousTaskLoadException("Unable to load task with ID '$taskId'", e)
        }

        // Ensure the task was found
        if (!task) {
            throw new PersistentAsynchronousTaskNotFoundException("Task with ID '$taskId' was not found")
        }
    }

    /**
     * Returns the task's name.
     *
     * @return
     */
    @Override
    public abstract String getTaskName()

    /**
     * Returns the task's ID.
     *
     * @return
     */
    @Override
    public int getTaskId() {
        return task.id
    }

    /**
     * Returns the task's progress.
     *
     * @return
     */
    @Override
    public int getProgress() {
        return task.progress
    }

    /**
     * Returns the results associated with a task that has ended.
     *
     * @return
     */
    @Override
    public Object getResults() {
        return unmarshall(task.results)
    }

    /**
     * Gets the current state of the task.
     *
     * @return
     */
    @Override
    public AsynchronousTaskState getState() {
        return task.state
    }

    /**
     * Gets the time when the task was created.
     *
     * @return
     */
    @Override
    public Date getCreatedTime() {
        return task.dateCreated
    }

    /**
     * Gets the time when the task was last updated.
     *
     * @return
     */
    @Override
    public Date getUpdatedTime() {
        return task.lastUpdated
    }

    /**
     * Gets the time when the task was started.
     *
     * @return
     */
    @Override
    public Date getStartTime() {
        return task.startTime
    }

    /**
     * Gets the time when the task was ended.
     *
     * @return
     */
    @Override
    public Date getEndTime() {
        return task.endTime
    }

    /**
     * Starts a task.
     */
    @Override
    protected void start() {
        start(null)
    }

    /**
     * Starts a task.
     *
     * @param description Description of the current step in the overall process of the task.
     */
    @Override
    protected void start(String description) {
        task.description = description
        task.state = AsynchronousTaskState.RUNNING
        task.progress = 0
        task.save(failOnError: true)
    }

    /**
     * Updates the progress of the task.
     *
     * @param progress Task's percentage complete.
     */
    @Override
    protected void update(int progress) {
        update(progress, task.description)
    }

    /**
     * Updates the progress of the task.
     *
     * @param progress Task's percentage complete.
     * @param description Description of the current step in the overall process of the task.
     */
    @Override
    protected void update(int progress, String description) {
        task.progress = progress
        task.description = description
        task.save(failOnError: true)
    }

    /**
     * Sets the task in an error state.
     */
    @Override
    protected void error() {
        completeTask(AsynchronousTaskState.ERROR)
    }

    /**
     * Sets the task in an error state.
     *
     * @param results
     */
    @Override
    protected void error(Object results) {
        completeTask(AsynchronousTaskState.ERROR, results)
    }

    /**
     * Sets the task in a failure state.
     */
    @Override
    protected void failure() {
        completeTask(AsynchronousTaskState.FAILURE)
    }

    /**
     * Sets the task in a failure state.
     *
     * @param results
     */
    @Override
    protected void failure(Object results) {
        completeTask(AsynchronousTaskState.FAILURE, results)
    }

    /**
     * Completes the task.
     */
    @Override
    protected void complete() {
        completeTask(AsynchronousTaskState.COMPLETED)
    }

    /**
     * Completes the task.
     *
     * @param results
     */
    @Override
    protected void complete(Object results) {
        // Set the progress to 100% for successfully completed tasks.
        task.progress = 100

        // Complete the task.
        completeTask(AsynchronousTaskState.COMPLETED, results)
    }

    /**
     * Completes the task with the given state and results.
     *
     * @param state End state of the task.
     */
    private void completeTask(AsynchronousTaskState state) {
        completeTask(state, null)
    }

    /**
     * Completes the task with the given state and results.
     *
     * @param state End state of the task.
     * @param results Data associated with the completion of the task.
     */
    private void completeTask(AsynchronousTaskState state, Object results) {
        task.state = state
        task.results = marshall(results)
        task.endTime = new Date()
        task.save(flush: true, failOnError: true)
    }

    /**
     * Converts the results associated with the task to a string.
     *
     * @param results
     * @return
     */
    private String marshall(Object results) {
        // Nothing to do if it's already a string
        if (results instanceof String) {
            return results
        }

        // Check for JSON conversion
        if (results.getClass() in [List, Map]) {
            return new JsonBuilder(results).toString()
        }

        // Return the string representation of the object
        return results.toString()
    }

    /**
     * Un-marshalls results stored in the database.
     *
     * @param results
     * @return
     */
    private Object unmarshall(String results) {
        // Attempt to convert from JSON
        try {
            return new JsonSlurper().parseText(results)
        }
        catch (Exception e) {
            // Continue
        }

        return results
    }

    /**
     * Returns the internal task state data.
     *
     * @return
     */
    protected Object getInternalTaskData() {
        return unmarshall(task.internalTaskData)
    }

    /**
     * Sets the internal task state data.
     *
     * @param data
     */
    protected void setInternalTaskData(Object data) {
        task.internalTaskData = marshall(data)
    }
}
