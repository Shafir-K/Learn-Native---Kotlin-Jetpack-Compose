package com.shafir.todoapp.features.notes.presentation.notes.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shafir.todoapp.features.notes.domain.util.NoteOrder
import com.shafir.todoapp.features.notes.domain.util.OrderType

@Composable
fun orderSection(
    modifier: Modifier = Modifier,
    noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    orderChange: (NoteOrder) -> Unit
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "Title",
                checked = noteOrder is NoteOrder.Title,
                onCheck = {
                    orderChange(NoteOrder.Title(noteOrder.orderType))
                })
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Date",
                checked = noteOrder is NoteOrder.Date,
                onCheck = {
                    orderChange(NoteOrder.Date(noteOrder.orderType))
                })
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Color",
                checked = noteOrder is NoteOrder.Color,
                onCheck = {
                    orderChange(NoteOrder.Color(noteOrder.orderType))
                })
            Spacer(modifier = Modifier.width(8.dp))

        }
        Spacer(Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            DefaultRadioButton(
                text = "Ascending",
                checked = noteOrder.orderType is OrderType.Ascending,
                onCheck = {
                    orderChange(noteOrder.copy(OrderType.Ascending))
                })
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Descending",
                checked = noteOrder.orderType is OrderType.Descending,
                onCheck = {
                    orderChange(noteOrder.copy(OrderType.Descending))
                })

        }


    }
}