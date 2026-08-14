package dev.danielkindl.luvoq.model

import kotlinx.serialization.Serializable

@Serializable
sealed interface Action {
    val id: ActionId
    val typeId: CapabilityTypeId

    @Serializable
    data class SetVolume(
        override val id: ActionId,
        val stream: VolumeStream,
        val levelPercent: Int,
    ) : Action {
        override val typeId = when (stream) {
            VolumeStream.MEDIA -> CapabilityTypeId("action.set_media_volume")
            VolumeStream.RINGTONE -> CapabilityTypeId("action.set_ringtone_volume")
            VolumeStream.ALARM -> CapabilityTypeId("action.set_alarm_volume")
        }

        init {
            require(id.isValid()) { "Action ID must not be blank" }
            require(levelPercent in 0..100) { "Volume must be between 0 and 100" }
        }
    }

    @Serializable
    data class SetBrightness(
        override val id: ActionId,
        val levelPercent: Int,
    ) : Action {
        override val typeId = CapabilityTypeId("action.set_brightness")

        init {
            require(id.isValid()) { "Action ID must not be blank" }
            require(levelPercent in 0..100) { "Brightness must be between 0 and 100" }
        }
    }

    @Serializable
    data class OpenApp(
        override val id: ActionId,
        val packageName: String,
    ) : Action {
        override val typeId = CapabilityTypeId("action.open_app")

        init {
            require(id.isValid()) { "Action ID must not be blank" }
            require(packageName.isNotBlank()) { "Package name must not be blank" }
        }
    }

    @Serializable
    data class ShowLuvoqNotification(
        override val id: ActionId,
        val message: String,
    ) : Action {
        override val typeId = CapabilityTypeId("action.show_notification")

        init {
            require(id.isValid()) { "Action ID must not be blank" }
            require(message.isNotBlank()) { "Notification message must not be blank" }
        }
    }

    @Serializable
    data class Vibrate(override val id: ActionId) : Action {
        override val typeId = CapabilityTypeId("action.vibrate")

        init {
            require(id.isValid()) { "Action ID must not be blank" }
        }
    }

    @Serializable
    data class DoNotDisturb(
        override val id: ActionId,
        val enabled: Boolean,
    ) : Action {
        override val typeId = CapabilityTypeId("action.do_not_disturb")

        init {
            require(id.isValid()) { "Action ID must not be blank" }
        }
    }
}

@Serializable
enum class VolumeStream {
    MEDIA,
    RINGTONE,
    ALARM,
}
