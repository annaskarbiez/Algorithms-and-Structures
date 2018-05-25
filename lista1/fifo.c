#include <stdio.h>
#include <stdlib.h>

struct Node
{
    struct Node *prev;
    int value;
};

struct Queue
{
    struct Node *head;
    struct Node *tail;
};

struct Queue *newQueue()
{
    struct Queue *queue = malloc(sizeof(struct Queue));
    queue->head = NULL;
    queue->tail = NULL;
    return queue;
}

void addItem(struct Queue *queue, int value)
{
    if (queue->tail != NULL)
    {
        struct Node *newItem = malloc(sizeof(struct Node));

        newItem->value = value;
        newItem->prev = NULL;

        queue->tail->prev = newItem;
        queue->tail = newItem;
    }
    else
    {
        struct Node *newItem = malloc(sizeof(struct Node));

        newItem->value = value;
        newItem->prev = NULL;

        queue->tail = newItem;
        queue->head = newItem;
    }
}

int getItem(struct Queue *queue)
{
    if (queue->head == NULL)
    {
        printf("Empty queue, result: ");
        return 0;
    }

    struct Node *temporary = queue->head;
    int item = temporary->value;
    queue->head = temporary->prev;

    if (queue->tail == temporary)
    {
        queue->tail = NULL;
    }

    free(temporary);
    return item;
}

int main()
{
    struct Queue *queue = newQueue();
    
    addItem(queue, 1);
    addItem(queue, 2);
    addItem(queue, 3);
    printf("%d \n", getItem(queue));
    printf("%d \n", getItem(queue));
    printf("%d \n", getItem(queue));
    printf("%d \n", getItem(queue));
    printf("%d \n", getItem(queue));
    addItem(queue, 4);
    addItem(queue, 5);
    addItem(queue, 6);
    printf("%d \n", getItem(queue));
    printf("%d \n", getItem(queue));

    return 0;
}