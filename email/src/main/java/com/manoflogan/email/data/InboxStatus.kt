package com.manoflogan.email.data

/**
 * Inbox status
 */
sealed interface InboxStatus {
    object Loading: InboxStatus
    data class HasEmails(
        val emails: List<Email>
    ): InboxStatus
    object Error: InboxStatus
    object Empty: InboxStatus
}