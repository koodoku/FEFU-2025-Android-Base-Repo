package co.feip.fefu2025.presentation.screen.details.search

interface SearchScreenEvent {
    object clearQuery : SearchScreenEvent
    data class onSearch(val query: String) : SearchScreenEvent
}