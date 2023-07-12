package com.namnp.androidjetpack.design_pattern

enum class DialogType {
    DIALOG_CREATE_CHAT,
    DIALOG_DELETE_CHAT,
    DIALOG_EDIT_CHAT,
}

sealed class Dialog {
    object CreateChatDialog : Dialog()
    object DeleteChatDialog : Dialog()
    object EditChatDialog : Dialog()
}

object DialogFactory {

    fun createDialog(dialogType: DialogType): Dialog {
        return when(dialogType) {
            DialogType.DIALOG_CREATE_CHAT -> Dialog.CreateChatDialog
            DialogType.DIALOG_DELETE_CHAT -> Dialog.DeleteChatDialog
            DialogType.DIALOG_EDIT_CHAT -> Dialog.EditChatDialog
        }
    }
}