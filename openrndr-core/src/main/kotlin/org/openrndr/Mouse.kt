package org.openrndr

import mu.KotlinLogging
import org.openrndr.events.Event
import org.openrndr.math.Vector2

private val logger = KotlinLogging.logger {}

/**
 * Mouse event message
 */
data class MouseEvent(val position: Vector2, val rotation: Vector2, val dragDisplacement: Vector2, val type: MouseEventType, val button: MouseButton, val modifiers: Set<KeyModifier>) {
    /**
     * specifies if the propagation of this event is cancelled
     * @see cancelPropagation
     */
    var propagationCancelled: Boolean = false

    /**
     * cancel propagation of this [MouseEvent] message
     *
     * Note that cancellation is only a hint. Event listeners should check if [MouseEvent.propagationCancelled] is set to see
     * if another listener already consumed the event.
     * @see propagationCancelled
     */
    fun cancelPropagation() {
        logger.debug { "Cancelling propagation of $type event" }
        propagationCancelled = true
    }
}

/**
 * Mouse events
 */
class Mouse(private val application: () -> Application) {
    /**
     * The current mouse position
     */
    var position: Vector2
        get() = application().cursorPosition
        set(value) {
            application().cursorPosition = value
        }

    /**
     * specifies if cursor should be visible
     */
    var cursorVisible: Boolean
        get() = application().cursorVisible
        set(value) {
            application().cursorVisible = value
        }

    /**
     * mouse button down event
     *
     * Emitted from [Application] whenever a mouse button is pressed
     */
    val buttonDown = Event<MouseEvent>("mouse-button-down", postpone = true)

    /**
     * mouse button up event
     *
     * Emitted from [Application] whenever a mouse button is released
     */
    val buttonUp = Event<MouseEvent>("mouse-button-up", postpone = true)

    /**
     * mouse dragged event
     *
     * Emitted from [Application] whenever the mouse is moved while a button is pressed
     */
    val dragged = Event<MouseEvent>("mouse-dragged", postpone = true)

    /**
     * mouse moved event
     *
     * Emitted from [Application] whenever the mouse is moved
     */
    val moved = Event<MouseEvent>("mouse-moved", postpone = true)
    /**
     * mouse scroll wheel event
     *
     * Emitted from [Application] whenever the mouse scroll wheel is used
     */
    val scrolled = Event<MouseEvent>("mouse-scrolled", postpone = true)

    @Deprecated("use buttonUp")
    val clicked = buttonUp

    /**
     * mouse entered event
     *
     * Emitted from [Application] whenever the mouse enters the window client area
     */
    val entered = Event<MouseEvent>("mouse-entered", postpone = true)

    /**
     * mouse exited event
     *
     * Emitted from [Application] whenever the mouse exits the window client area
     */
    val exited = Event<MouseEvent>("mouse-exited", postpone = true)

    var pressedButtons = mutableSetOf<MouseButton>()
}
